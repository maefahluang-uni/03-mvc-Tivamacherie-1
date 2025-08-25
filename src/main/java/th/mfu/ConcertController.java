package th.mfu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class ConcertController {

    private List<Concert> concerts = new ArrayList<>();
    private int nextId = 1;

    // เตรียม concert object ให้ฟอร์มใช้ (สำคัญ)
    @ModelAttribute("concert")
    public Concert newConcert() {
        return new Concert();
    }

    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        model.addAttribute("concerts", concerts);
        return "list-concert";
    }

    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        // Debug: print ข้อมูลที่ได้รับ
        System.out.println("Received concert: " + concert.getTitle() + ", " + concert.getPerformer() + ", " + concert.getDate());
        
        concert.setId(nextId++);
        concerts.add(concert);
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable int id) {
        Iterator<Concert> iterator = concerts.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                break;
            }
        }
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert")
    public String deleteAllConcerts() {
        concerts.clear();
        nextId = 1;
        return "redirect:/concerts";
    }
}
