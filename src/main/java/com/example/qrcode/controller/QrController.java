package com.example.qrcode.controller;

import com.example.qrcode.entity.TextEntry;
import com.example.qrcode.repository.TextEntryRepository;
import com.example.qrcode.service.QrCodeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QrController {

    private final TextEntryRepository repository;
    private final QrCodeService qrCodeService;

    public QrController(TextEntryRepository repository, QrCodeService qrCodeService) {
        this.repository = repository;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        prepareModel(model, null, null, null);
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@Valid @ModelAttribute TextEntry textEntry,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            prepareModel(model, null, null, "Vui lòng nhập nội dung không được để trống.");
            return "index";
        }

        TextEntry saved = repository.save(textEntry);
        try {
            String qrDataUri = qrCodeService.generateQrDataUri(saved.getText());
            prepareModel(model, qrDataUri, saved, null);
        } catch (Exception e) {
            prepareModel(model, null, saved, "Không thể tạo QR Code: " + e.getMessage());
        }
        return "index";
    }

    private void prepareModel(Model model, String qrDataUri, TextEntry lastEntry, String error) {
        model.addAttribute("entries", repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")));
        model.addAttribute("textEntry", new TextEntry());
        model.addAttribute("qrDataUri", qrDataUri);
        model.addAttribute("lastEntry", lastEntry);
        model.addAttribute("error", error);
    }
}
