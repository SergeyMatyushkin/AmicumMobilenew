package com.example.models

/**
 * Класс подразделений - элемент
 * Company:
 *      []
 *          {
 *              id:	                    1
 *              title:	                "Ростехнадзор"
 *              upper_company_id:   	null
 *              departments:	        [Department]
 *              companies:	            [Company]
 *          }
 *  Department:
 *          {
 *              id:	                    1
 *              title:	                "Ростехнадзор"
 *         }
 */
class Company(
    var id: Int,
    var title: String,
    var upper_company_id: Int,
    var departments: ArrayList<Department>,
    var companies: ArrayList<Company>
)

/**
 * Класс подразделений
 */
data class Department(
    val id: Int,                        // ключ подразделения
    val title: String,                  // наименование подразделения
)

