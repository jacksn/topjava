Java Enterprise Online Project 
===============================

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/aff9dce9408c4bffa360c6dd9c072c72)](https://www.codacy.com/app/gml-jackson/topjava?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jacksn/topjava&amp;utm_campaign=Badge_Grade)
[![Dependency Status](https://dependencyci.com/github/jacksn/topjava/badge)](https://dependencyci.com/github/jacksn/topjava)
[![Build Status](https://travis-ci.org/jacksn/topjava.svg?branch=master)](https://travis-ci.org/jacksn/topjava)

#### <a href="https://github.com/JavaOPs/topjava">Java Enterprise проект</a> с регистрацией и авторизацией и интерфейсом на основе ролей (USER, ADMIN). Администратор может создавать/редактировать/удалять/пользователей, а пользователь - управлять своим профилем и данными (день, еда, калории) через UI (по AJAX) и по REST интерфейсу с базовой авторизацией. Возможна фильтрация данных по датам и времени, при этом цвет записи таблицы еды зависит от того, превышает ли сумма калорий за день норму (редактируемый параметр в профиле пользователя). Весь REST интерфейс покрывается JUnit тестами, используя Spring MVC Test и Spring Security Test.
