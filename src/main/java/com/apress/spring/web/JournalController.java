package com.apress.spring.web;

import com.apress.spring.domain.Journal;
import com.apress.spring.domain.JournalEntry;
import com.apress.spring.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JournalController {

    private static List<JournalEntry> entries = new ArrayList<>();
    static {
        try{
            entries.add(new JournalEntry("Get to know Spring Boot", "Today I will learn Spring Boot", "01/01/2016"));
            entries.add(new JournalEntry("Simple Spring Boot Project", "I will do my first Spring Boot Project", "01/02/2016"));
            entries.add(new JournalEntry("Spring Boot Reading", "Read more about Spring Boot", "02/01/2016"));
            entries.add(new JournalEntry("Spring Boot in the Cloud", "Spring Boot using Cloud Foundry", "03/01/2016"));
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Autowired
    JournalRepository repo;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("journal", repo.findAll());
        return "index";
    }

    @RequestMapping(value = "/journal", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody List<Journal> getJournal(){
        return repo.findAll();
    }

    @RequestMapping(value = "/journal/all")
    public @ResponseBody List<JournalEntry> getAllJournal() throws ParseException{
        return entries;
    }

    @RequestMapping(value = "/journal/findBy/title/{title}")
    public List<JournalEntry> findByTitleContains(@PathVariable String title) throws ParseException{
        return entries.stream().filter(journalEntry -> journalEntry.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/journal", method = RequestMethod.POST)
    public JournalEntry add(@RequestBody JournalEntry entry){
        entries.add(entry);
        return entry;
    }

}
