package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Properties;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class RootController extends AbstractUserController {

    @Autowired
    private UserService service;

    @Autowired
    private CustomReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

    @PostMapping("/register")
    public void saveRegister(@Valid UserTo userTo) {
        User user = UserUtil.createNewFromTo(userTo);
        checkNew(user);
        log.info("create " + user);
        service.save(user);
    }

    @GetMapping(value = "/i18n/{locale}")
    public Properties getLocal(@PathVariable String locale) {
        return reloadableResourceBundleMessageSource.getAllMessages(new Locale(locale));
    }
}
