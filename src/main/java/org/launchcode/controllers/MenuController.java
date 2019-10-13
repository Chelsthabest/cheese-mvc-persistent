package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value ="menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("menus",menuDao.findAll());
        model.addAttribute("title", "Menu Items");
        return "menu/index";
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String addMenu(Model model){
        model.addAttribute("title","Add Menu Item");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public String processMenu(@ModelAttribute @Valid Menu newMenu, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("title","Add Menu Item");
            return "menu/add";
        }
        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{menuId}")
    public String viewMenu(Model model, @PathVariable int menuId){
        Menu menu = menuDao.findOne(menuId);
        model.addAttribute("menu",menu);
        model.addAttribute("title",menu.getName());

        return "menu/view";
    }
    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model,@PathVariable int menuId){

        Menu menu = menuDao.findOne(menuId);

        AddMenuItemForm menuForm = new AddMenuItemForm(menu, cheeseDao.findAll());
        model.addAttribute("title","Add item to menu: "+ menu.getName());
        model.addAttribute("form",menuForm);

        return "menu/add-item";
    }

    @RequestMapping(value="add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm addMenuItemForm, Errors errors,@RequestParam int menuId, @RequestParam int cheeseId) {
        if (errors.hasErrors()) {
            return "menu/add-item";
        }

        Menu menu = menuDao.findOne(menuId);
        Cheese cheese = cheeseDao.findOne(cheeseId);
        menu.addItem(cheese);
        menuDao.save(menu);

        return "redirect:/menu/view/" + menu.getId();
    }
}