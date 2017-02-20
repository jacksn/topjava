<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="true">
            ${pageContext.response.locale}<b class="caret"></b>
    </a>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
        <li><a onclick="setLanguage('en')">English</a></li>
        <li><a onclick="setLanguage('ru')">Русский</a></li>
    </ul>
    <script type="text/javascript">
        var localeCode = "en";
        function setLanguage(lang) {
            window.location.href = window.location.href.split('?')[0] + '?lang=' + lang;
        }
    </script>
</li>
