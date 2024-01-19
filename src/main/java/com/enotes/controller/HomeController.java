package com.enotes.controller;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.enotes.entity.Note;
import com.enotes.entity.User;
import com.enotes.repositories.UserRepo;
import com.enotes.services.NoteService;
import com.enotes.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @ModelAttribute
    public User getUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            if (user != null) {
                m.addAttribute("user", user);
                return user;
            }
        }
        // Handle the case where the user or principal is null
        return null;
    }

    @GetMapping("/")
    public String Home() {
        return "index";
    }

    @GetMapping("/user")
    public String HomeAfterLogin() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/user/editNotes/{id}")
    public String editNotes(@PathVariable("id") int id, Model m) {
        Note note = noteService.findById(id);
        m.addAttribute("note", note);
        return "edit_notes";
    }

    @GetMapping("/user/viewNotes")
    public String viewNotes(Model m, Principal p) {
        User user = getUser(p, m);
        List<Note> notes = noteService.getNotesByUser(user);
        m.addAttribute("notes", notes);
        return "view_notes";
    }

    @GetMapping("/user/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session, HttpServletRequest req) {
        String url = req.getRequestURL().toString();
        url = url.replace(req.getServletPath(), "");
        User u = (User) userService.saveUser(user, url);
        if (u != null) {
            session.setAttribute("msg", "User Creted Successfuly");
            return "register";
        } else
            session.setAttribute("msg", "User failed to create");
        return "register";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, org.springframework.ui.Model m) {
        if (userService.verifyAccount(code)) {
            m.addAttribute("msg", "account verified!");
        } else
            m.addAttribute("msg", "account verification failed!");
        return "message";
    }

    @PostMapping("/user/saveNote")
    public String addNote(@ModelAttribute Note note, HttpSession session, Principal p, Model m) {
        note.setUser(getUser(p, m));
        if (note.getContent().length() > 1000) { // Adjust the maximum length here
            m.addAttribute("msg", "Content is too long! Please limit it to 4000 characters.");
            return "add_notes";
        }
        note.setDate(Date.valueOf(LocalDate.now()));
        Note n = noteService.addNote(note);
        if (n != null) {
            m.addAttribute("msg", n);
        } else
            m.addAttribute("msg", "Note is null");

        return "redirect:/user/addNotes";
    }

    @GetMapping("/user/addNotes")
    public String addNotePage() {
        return "add_notes";
    }

    @PostMapping("/user/updateNote/{id}")
    public String updateNote(@PathVariable("id") int id, @ModelAttribute Note note, HttpSession session, Principal p,
            Model m) {
        note.setUser(getUser(p, m));
        if (note.getContent().length() > 1000) { // Adjust the maximum length here
            m.addAttribute("msg", "Content is too long! Please limit it to 4000 characters.");
            return "add_notes";
        }
        note.setDate(Date.valueOf(LocalDate.now()));
        Note n = noteService.updateNote(note, id);
        if (n != null) {
            m.addAttribute("msg", n);
        } else
            m.addAttribute("msg", "Note is null");

        return "redirect:/user/viewNotes";
    }

    @GetMapping("/user/error")
    public String error() {
        return "error";
    }

}