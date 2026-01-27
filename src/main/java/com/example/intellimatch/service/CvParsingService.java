package com.example.intellimatch.service;

import com.example.intellimatch.dto.CvExtractionResult;
import com.example.intellimatch.entity.Skill;
import com.example.intellimatch.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CvParsingService {

    private final SkillRepository skillRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("(\\+\\d{1,3}[- ]?)?\\d{10}");


    public CvExtractionResult extractCv(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        if(fileName == null || !fileName.endsWith(".pdf")) {
            throw new RuntimeException("Only pdf files are supported");
        }

        String text;
        try {
            text = extractPdf(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse PDF content", e);
        }

        return CvExtractionResult.builder()
                .rawText(text)
                .extractedEmail(extractEmail(text))
                .extractedPhone(extractPhone(text))
                .extractedSkills(extractSkills(text))
                .build();
    }

    private String extractPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(document);
        }
    }

    private String extractEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.find() ? matcher.group() : null;
    }

    private String extractPhone(String phone) {
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.find() ? matcher.group() : null;
    }

    private static Pattern skillPattern(String skill) {
        return Pattern.compile(
                "(?i)(^|\\W)" + Pattern.quote(skill) + "(\\W|$)"
        );
    }

    private Set<String> extractSkills(String text){
        String lowerCaseText = text.toLowerCase();

        return skillRepository.findAll().stream()
                .map(Skill::getName)
                .filter(skill ->
                        skillPattern(skill).matcher(text).find()
                )
                .collect(Collectors.toSet());
    }
}
