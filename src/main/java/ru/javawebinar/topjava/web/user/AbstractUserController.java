package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * User: gkislin
 */
public abstract class AbstractUserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserService service;

    @Autowired
    protected MessageSource messageSource;

    public List<User> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public User create(User user) {
        checkNew(user);
        LOG.info("create " + user);
        try {
            return service.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    messageSource.getMessage("users.email.duplicate", null,
                            LocaleContextHolder.getLocale()));
        }
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void update(User user, int id) {
        LOG.info("update " + user);
        checkIdConsistent(user, id);
        try {
            service.update(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    messageSource.getMessage("users.email.duplicate", null,
                            LocaleContextHolder.getLocale()));
        }
    }

    public void update(UserTo userTo) {
        LOG.info("update " + userTo);
        checkIdConsistent(userTo, AuthorizedUser.id());
        try {
            service.update(userTo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    messageSource.getMessage("users.email.duplicate", null,
                            LocaleContextHolder.getLocale()));
        }
    }

    public User getByMail(String email) {
        LOG.info("getByEmail " + email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        LOG.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }
}
