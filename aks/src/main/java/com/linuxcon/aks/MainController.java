package com.linuxcon.aks;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController {
	@Autowired
	private ImageProcessor imageProcessor;

	@RequestMapping("/")
    public String index() {
        return "forward:/query";
    }
	
	@GetMapping("/query")
	public String search(Model model, HttpSession session) {
		model.addAttribute("imagefile", new ImageFile());
		return "query";
	}
	
	

	@RequestMapping(value = "/doquery")
	public String doQuery(Model model, ImageFile imageFile, HttpSession session) {
		System.out.println("image file:" + imageFile.getFileURL());
		String fileURL = imageFile.getFileURL();
		String result = imageProcessor.processImage(imageFile);
		imageFile.setFileURL(fileURL);
		imageFile.setResult(result);
		model.addAttribute("imagefile", imageFile);
		return "query";
	}

}
