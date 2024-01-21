package com.tuyenngoc.bookstore.exception;

import com.tuyenngoc.bookstore.base.RestData;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandle {

    private final MessageSource messageSource;

    /**
     * Xử lý ngoại lệ chung
     *
     * @param ex Ngoại lệ Exception
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestData<?>> handlerInternalServerError(Exception ex) {
        log.error("Internal Server Error: {}", ex.getMessage(), ex);
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_EXCEPTION_GENERAL, null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi có lỗi xảy ra do vi phạm ràng buộc.
     *
     * @param ex Ngoại lệ ConstraintViolationException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 400 Bad Request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestData<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> result = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
            String errorMessage = messageSource.getMessage(Objects.requireNonNull(error.getMessage()), null,
                    LocaleContextHolder.getLocale());
            result.put(fieldName, errorMessage);
        });
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, result);
    }

    /**
     * Xử lý ngoại lệ khi có lỗi xảy ra trong quá trình binding dữ liệu.
     *
     * @param ex Ngoại lệ BindException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 400 Bad Request
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestData<?>> handleValidException(BindException ex) {
        Map<String, String> result = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()), null,
                    LocaleContextHolder.getLocale());
            result.put(fieldName, errorMessage);
        });
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, result);
    }

    /**
     * Xử lý ngoại lệ khi không tìm thấy tài nguyên.
     *
     * @param ex Ngoại lệ NotFoundException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 404 Not Found
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestData<?>> handlerNotFoundException(NotFoundException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(ex.getStatus(), errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi có yêu cầu không hợp lệ.
     *
     * @param ex Ngoại lệ InvalidException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 400 Bad Request
     */
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestData<?>> handlerInvalidException(InvalidException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(ex.getStatus(), errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi có lỗi trong quá trình tải lên tệp tin.
     *
     * @param ex Ngoại lệ UploadFileException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 502 Bad Gateway
     */
    @ExceptionHandler(UploadFileException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseEntity<RestData<?>> handleUploadImageException(UploadFileException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(ex.getStatus(), errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi người dùng không có quyền truy cập.
     *
     * @param ex Ngoại lệ UnauthorizedException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 401 Unauthorized
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RestData<?>> handleUnauthorizedException(UnauthorizedException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(ex.getStatus(), errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi người dùng không có quyền truy cập.
     *
     * @param ex Ngoại lệ ForbiddenException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 403 Forbidden
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<RestData<?>> handleAccessDeniedException(AccessDeniedException ex) {
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_FORBIDDEN, null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.FORBIDDEN, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi có lỗi Integrity Violation trong cơ sở dữ liệu.
     *
     * @param ex Ngoại lệ DataIntegrityViolationException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 409
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestData<?>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(ex.getStatus(), errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi yêu cầu không có nội dung.
     *
     * @param ex Ngoại lệ HttpMessageNotReadableException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 400 Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestData<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_REQUEST_BODY, null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi phương thức HTTP không được hỗ trợ.
     *
     * @param ex Ngoại lệ HttpRequestMethodNotSupportedException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 405 Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<RestData<?>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_METHOD_NOT_SUPPORTED, ex.getSupportedMethods(), LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi thiếu tham số trong request.
     *
     * @param ex Ngoại lệ MissingServletRequestParameterException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 400 Bad request
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestData<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_REQUIRED_MISSING_PARAMETER, new Object[]{parameterName}, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi giá trị tham số không phù hợp.
     *
     * @param ex Ngoại lệ MethodArgumentTypeMismatchException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestData<?>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getParameter().getParameterName();
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_METHOD_ARGUMENT_TYPE_MISMATCH, new Object[]{parameterName}, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi không tìm thấy tài nguyên tĩnh.
     *
     * @param ex Ngoại lệ NoResourceFoundException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 404 Not Found
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestData<?>> handleNoResourceFoundException(NoResourceFoundException ex) {
        String resourcePath = ex.getResourcePath();
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_RESOURCE_NOT_FOUND, new Object[]{resourcePath}, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.NOT_FOUND, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi tải lên tệp tin vượt quá kích thước tối đa cho phép.
     *
     * @param ex Ngoại lệ MaxUploadSizeExceededException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 413 Payload Too Large
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<RestData<?>> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        String errorMessage = messageSource.getMessage(ErrorMessage.INVALID_MAX_UPLOAD_SIZE_EXCEEDED, null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.PAYLOAD_TOO_LARGE, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi thiếu một phần của yêu cầu.
     *
     * @param ex Ngoại lệ MissingServletRequestPartException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 400 Bad request
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestData<?>> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        String partName = ex.getRequestPartName();
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_MISSING_SERVLET_REQUEST_PART, new Object[]{partName}, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi Content-Type không được hỗ trợ.
     *
     * @param ex Ngoại lệ HttpMediaTypeNotSupportedException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 415 Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<RestData<?>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        String supportedContentType = ex.getSupportedMediaTypes().stream()
                .map(MediaType::toString)
                .collect(Collectors.joining());
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_UNSUPPORTED_MEDIA_TYPE, new Object[]{supportedContentType}, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errorMessage);
    }

    /**
     * Xử lý ngoại lệ khi có lỗi trong quá trình xử lý multipart.
     *
     * @param ex Ngoại lệ MultipartException
     * @return ResponseEntity chứa thông tin lỗi và mã HTTP 500 Internal Server Error
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestData<?>> handleMultipartException(MultipartException ex) {
        log.error("Multipart Exception: {}", ex.getMessage(), ex);
        String errorMessage = messageSource.getMessage(ErrorMessage.ERR_MULTIPART_EXCEPTION, null, LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

}
