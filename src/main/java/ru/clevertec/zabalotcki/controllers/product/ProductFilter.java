package ru.clevertec.zabalotcki.controllers.product;

import ru.clevertec.zabalotcki.controllers.filter.CustomFilterSaveUpdate;

import javax.servlet.annotation.WebFilter;

@WebFilter("/product/add")
public class ProductFilter extends CustomFilterSaveUpdate {
}
