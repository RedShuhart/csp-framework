package com.tsovedenski.csp.scheduling.pwr.domain

/**
 * Created by Tsvetan Ovedenski on 2019-02-01.
 */
enum class Type (val code: Char) {
    Lecture ('W'),
    Class ('C'),
    Lab ('L'),
    Project ('P'),
    Seminar ('S'),
    Other ('O')
}