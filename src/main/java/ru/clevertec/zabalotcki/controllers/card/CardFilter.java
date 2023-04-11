package ru.clevertec.zabalotcki.controllers.card;

import ru.clevertec.zabalotcki.controllers.filter.CustomFilterSaveUpdate;

import javax.servlet.annotation.WebFilter;

@WebFilter("/card/add")
public class CardFilter extends CustomFilterSaveUpdate {
}
