package edu.practice.controllers;

import edu.practice.domain.Document;
import edu.practice.domain.RegCard;
import edu.practice.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path = "/documents")
public class DocumentsController {
    @Autowired
    private DocumentService documentService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("documents", documentService.findDocuments());
        return "documents/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("document", documentService.findDocumentById(id));
        return "documents/show";
    }

    @GetMapping("/new")
    public String newDocument(Model model) {
        model.addAttribute("document", new Document());
        model.addAttribute("regCard", new RegCard());
        return "documents/new";
    }

    @PostMapping
    public String create(@ModelAttribute("document") @Valid Document document,
                         BindingResult bindingResult, String path) throws IOException {
        if (bindingResult.hasErrors())
            return "documents/new";
        documentService.loadDocument(document.getDocumentName(), document.getAuthor(), document.getRegCard().getDocumentIntroNumber(), document.getTempPath());
        return "redirect:/documents";
    }

    @GetMapping("/{id}/deregister")
    public String deregister(Model model, @PathVariable("id") int id) {
        model.addAttribute("document", documentService.findDocumentById(id));
        return "documents/deregister";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("document") Document document, @PathVariable("id") int id) {
        documentService.deregisterDocument(id, document.getRegCard().getDocumentExternNumber());
        return "redirect:/documents";
    }

    @GetMapping("/{id}/download")
    public String downloadCall(Model model, @PathVariable("id") int id) {
        model.addAttribute("document", documentService.findDocumentById(id));
        return "documents/download";
    }

    @PostMapping("/{id}/download")
    public String download(@ModelAttribute("document") Document document, @PathVariable("id") int id) throws IOException {
        documentService.downloadDocument(id, document.getTempPath());
        return "redirect:/documents";
    }
}
