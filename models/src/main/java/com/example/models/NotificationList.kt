package com.example.models

/**
 * Класс уведомлений - элемент
 * Notifications:[
 *      {
 *          id:                 "medicalExam"
 *          title:              "Запланированный медицинский осмотр"
 *          notifications:      NotificationMedicalExamWorker,
 *      }
 *  ]
 */
class NotificationList<T>(
    var id: String,
    var title: String,
    var notifications: ArrayList<T>
)

/**
 * Класс уведомления
 */
data class Notification(
    // Сведения о работнике
    val worker_id: Int,                     // ключ работника
    val worker_full_name: String,           // ФИО
    val worker_staff_number: String,        // табельный номер работника
    val worker_position_title: String,      // должность

    // СИЗ
    val siz_id: Int,                        // ключ СИЗ
    val siz_title: String,                  // название СИЗ

    // Запланированный медицинский осмотр
    val checkup_date_start: String,         // дата начала медосмотра
    val checkup_date_end: String,           // дата окончания медосмотра
    val flag: Boolean,                      // true  - если до окончания срока медосмотра осталось 2 недели или менее, то возвращается ораньжевый цвет. false - иначе срок замены просрочен, то возвращается красный цвет. null  - во всех остальных случаях
    val status_id: Int,                     // статус уведомления (прочитан-19 или нет-1)

    // ППК
    val ppk_id: Int,                        // ключ ППК
    val ppk_date_time: String,              // дата ППК
    val ppk_status_id: Int,                 // ключ статуса ППК Выдано, просрочено, выполнено и т.д.
    val checking_id: Int,                   // ключ проверки
    val injunction_id: Int,                 // ключ нарушения

    // Запланированная проверка ППК
    val audit_id: Int,                      // ключ запланированной проверки
    val audit_place_id: Int,                // ключ места
    val audit_place_title: String,          // название места
    val audit_date_time: String,            // дата запланированного аудита

    // Запланирована проверка знаний
    val check_knowledge_date_time: String,  // дата запланированной проверки знаний

    // Запланирован инструктаж
    val briefing_date_time: String,         // дата запланированного инструктажа

    //
)

