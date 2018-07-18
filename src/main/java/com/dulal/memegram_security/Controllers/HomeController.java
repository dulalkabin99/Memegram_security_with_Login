package com.dulal.memegram_security.Controllers;




import com.cloudinary.utils.ObjectUtils;
import com.dulal.memegram_security.Configurations.CloudinaryConfig;
import com.dulal.memegram_security.Models.MemeGram;
import com.dulal.memegram_security.Models.User;
import com.dulal.memegram_security.Repos.ContentRepo;
import com.dulal.memegram_security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {


    @Autowired
    ContentRepo contentRepo;

    @Autowired
    CloudinaryConfig cloudc;


    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String contentList(Model model) {
        model.addAttribute("contents", contentRepo.findAll());
        return "list";

    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value ="/register", method = RequestMethod.POST)
    public String processRegistration(@Valid @ModelAttribute("user") User user,
                                      BindingResult result, Model model){
        model.addAttribute("user", user);

        if(result.hasErrors()){
            return "registration";
        } else{
            userService.saveUser(user);
            model.addAttribute("message", "User Account SuccessFully Created");
        }
        return  "login";
    }

    @GetMapping("/add")
    public String messageForm(Model model) {
        model.addAttribute("memeGram", new MemeGram());
        return "addform";
    }

    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute("memeGram") MemeGram memeGram,
                              BindingResult result, @RequestParam("file")MultipartFile file) {
        if (result.hasErrors()) {
            return "addform";
        }

        else if (file.isEmpty()) {
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            memeGram.setHeadshot(uploadResult.get("url").toString());
            contentRepo.save(memeGram);
        }
        catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";

        }
        //contentRepo.save(memeGram);
        return "redirect:/";

    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }


    @RequestMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", contentRepo.findById(id).get());
        return "detail";
    }

    @RequestMapping("/update/{id}")
    public String updateContent(@PathVariable("id") long id, Model model) {
        model.addAttribute("memeGram", contentRepo.findById(id));
        return "addform";
    }


    @RequestMapping("/delete/{id}")
    public String deleteMeme(@PathVariable("id")long id){
        contentRepo.deleteById(id);
        return "redirect:/";

    }

}
