package th.mfu;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ConcertController {

    private static Map<Integer, Concert> concertMap = new HashMap<>();
    private static int nextId = 1;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/concert")
    public String listConcert(Model model) {
        model.addAttribute("concert", concertMap.values());
        return "list-concert";
    }

    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        model.addAttribute("concert", new Concert());
        return "add-concert-form";
    }

    @PostMapping("/concert")
    public String saveConcert(@ModelAttribute Concert concert) {
        concert.setId(nextId++);
        concertMap.put(concert.getId(), concert);
        return "redirect:/concert";
    }

    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable int id) {
        concertMap.remove(id);
        return "redirect:/concert";
    }

    @GetMapping("/delete-concert")
    public String removeAllConcerts() {
        concertMap.clear();
        nextId = 1;
        return "redirect:/concert";
    }
}
