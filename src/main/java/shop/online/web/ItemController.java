package shop.online.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.online.domain.item.Book;
import shop.online.domain.item.Item;
import shop.online.dto.BookForm;
import shop.online.service.ItemService;
import shop.online.web.validation.ItemValidation;

import javax.naming.Binding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    @GetMapping("items/new3")
    public String createForm(Model model){
        model.addAttribute("bookForm",new BookForm());
        return "items/item-form3";
    }
    @PostMapping("items/new3")
    public String create2(@Validated @ModelAttribute BookForm bookForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "items/item-form3";
        }
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";
    }
    @GetMapping("items")
    public String itemList(Model model){
        List<Item> items = itemService.findItems();

        List<BookForm> bookList = items
                .stream()
                .map(i->new BookForm((Book)i))
                .collect(Collectors.toList());
        model.addAttribute("items",bookList);

        return "items/item-list";
    }
    @GetMapping("item/edit/{itemId}")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm(item);
        model.addAttribute("form",form);
        return "items/update-item-form";
    }
    @PostMapping("item/edit")
    public String updateItem(BookForm form){
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }
}
