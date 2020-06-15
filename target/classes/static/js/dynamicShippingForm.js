function dynamicShipping(checkbox){
    if (checkbox.checked)
        $("#addShipForm").append("<div id=\"Catalog\">\n" +
            "<table>\n" +
            "<tr>\n" +
            "<th colspan=2>Shipping Address</th>\n" +
            "</tr>\n" +
            "\n" +
            "<tr>\n" +
            "<td>First name:</td>\n" +
            "<td>\n" +
            "<input type=\"text\"  th:field=\"*{shipToFirstName}\"/>\n" +
            "    </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "    <td>Last name:</td>\n" +
            "<td>\n" +
            "<input type=\"text\"  th:field=\"*{shipToLastName}\"/></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "    <td>Address 1:</td>\n" +
            "<td><input type=\"text\"  th:field=\"*{shipAddress1}\"/></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "    <td>Address 2:</td>\n" +
            "<td><input type=\"text\"  th:field=\"*{shipAddress2}\"/></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "    <td>City:</td>\n" +
            "<td><input type=\"text\"  th:field=\"*{shipCity}\"/></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "    <td>State:</td>\n" +
            "<td>><input type=\"text\"  th:field=\"*{shipState}\"/></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "    <td>Zip:</td>\n" +
            "<td><input type=\"text\"  th:field=\"*{shipZip}\"/></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "    <td>Country:</td>\n" +
            "<td><input type=\"text\"  th:field=\"*{shipCountry}\"/></td>\n" +
            "    </tr>\n" +
            "    </table>\n" +
            "    </div>");
    else $("#addShipForm").empty();
}