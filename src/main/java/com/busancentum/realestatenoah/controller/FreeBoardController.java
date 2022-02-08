package com.busancentum.realestatenoah.controller;

import com.busancentum.realestatenoah.dto.FreeBoardDTO;
import com.busancentum.realestatenoah.dto.PageRequestDTO;
import com.busancentum.realestatenoah.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/freeboard")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 어노테이션
public class FreeBoardController {

    private final FreeBoardService service; // final로 반드시 선언

    @GetMapping("/noah")
    public void mainpage() {
        log.info("get mainpage....");
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/freeboard/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("get list page........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {

        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(FreeBoardDTO dto, RedirectAttributes
            redirectAttributes) {

        log.info("dto...." + dto);

        // 새로 추가된 엔티티의 번호호
        Long go = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", dto);

        return "redirect:/freeboard/list";
    }

    /*@GetMapping("/read")
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("gno: " + gno);
        FreeBoardDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }
    */
    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO
                     requestDTO, Model model ){

        log.info("gno: " + gno);

        FreeBoardDTO dto = service.read(gno);

        model.addAttribute("dto", dto);

    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        log.info("gno: " + gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/freeboard/list";
    }

    @PostMapping("/modify")
    public String modify(FreeBoardDTO dto,
                         @ModelAttribute("requestDTO")PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){

        log.info("post modify.......................................");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/freeboard/read";
    }

    @GetMapping("/buy")
    public void buy() {
        log.info("buy.....");
    }

    @GetMapping("sell")
    public void sell() {
        log.info("sell....");
    }

    @GetMapping("/Rent")
    public void Rent() {
        log.info("Rent....");
    }

    @GetMapping("/Trade")
    public void Trade() {
        log.info("Trade....");
    }

}
