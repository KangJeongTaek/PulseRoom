package com.kjt.PulseRoom.exception

import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /* 잘못된 요청 에러*/
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException (e : IllegalArgumentException): ResponseEntity<Any>{
        logger.error(e.stackTraceToString())
        return ResponseEntity.badRequest().body(mapOf("result" to "fail", "msg" to e.message))
    }

    /* 없는 유저 요청 */
    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementException(e: NoSuchElementException) : ResponseEntity<Any>{
        logger.error(e.stackTraceToString())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("result" to  "fail", "msg" to "존재하지 않는 유저"))
    }

    /* DB 관련  */
    @ExceptionHandler(DataAccessException::class)
    fun dataAccessException(e: DataAccessException) : ResponseEntity<Any>{
        logger.error(e.stackTraceToString())
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("result" to "fail", "msg" to "내부 서버 오류 발생"))
    }
}