package com.example.models

/**
 * Модель пользователя, авторизованного в системе
 */
data class UserSession(
    var sessionLogin: String,                    // логин пользователя
    var sessionPassword: String,                 // пароль
    var userWorkstation: String,                 // рабочее место
    var employee_id: Int,                        // уникальный ключ человека
    var userName: String,                        // ФИО пользователя
    var last_name: String,                       // Фамилия пользователя
    var first_name: String,                      // Имя пользователя
    var patronymic: String,                      // Отчетство пользователя
    var position_id: Int,                        // ключ должности пользователя
    var position_title: String,                  // Должность пользователя
    var position_qualification: String,          // квалицификация пользователя
    var birthdate: String,                       // день рождения пользователя
    var worker_date_start: String,               // дата начала работы
    var worker_date_end: String,                 // дата окончания работы
    var userShift: String,                       // смену
    var userCompany: String,                     // наименование предприятия
    var userCompanyId: Int,                      // id Предприятия
    var userMineId: Int,                         // id Шахты
    var userMineTitle: String,                   // наименование Шахты
    var userDepartmentId: Int,                   // уникальный ключ департамента
    var userDepartmentTitle: String,             // название департамента справочное
    var userDepartmentPath: String,              // путь до подразделения пользователя
    var userWorkCompanyDepartmentId: Int,        // записываем главный ключ департамента подразделения (в котором он работает, меняться не может)
    var userCompanyDepartmentId: Int,            // записываем главный ключ департамента подразделения (выбранный департамент в менюшке)
    var userStaffNumber: String,                 // табельный номер сотрудника
    var worker_id: Int,                          // ключ работника
    var workerObject_ids: List<Int>,             // массив ключей конкретных воркеров (подземный/поверхностный)
    var session_id: String,                      // ключ сессии
    var worker_role: WorkerRole,                 // список ролей сотрудника (id, title)
    var socket_key: String,                      // случайный ключ сессии для вебсокета
    var user_image_src: String                   // путь до фотографии пользователя
)

/**
 * Модель роли пользователя
 */
data class WorkerRole(
    var id: Int,                                 // ключ роли
    var title: String                             // название роли
)