package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.dto.mapper.UserMapper;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

/**
 * REST-контроллер для управления пользователями.
 * Предоставляет CRUD-операции над ресурсом User.
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Создает нового пользователя.
     * Принимает DTO с email и именем, конвертирует в сущность, сохраняет и возвращает созданную запись.
     *
     * @param dto входные данные пользователя
     * @return ResponseEntity с DTO созданного пользователя и статусом 201 Created
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto dto) {
        log.debug("Request to create user: {}", dto);
        User entity = userMapper.toEntity(dto);
        User saved = userService.create(entity);
        log.info("User created successfully: id={}", saved.getId());

        return ResponseEntity
                .created(URI.create("/users/" + saved.getId()))
                .body(saved);
    }

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param id UUID пользователя
     * @return ResponseEntity с DTO пользователя (200 OK) или 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
        log.debug("Request to get user by id={}", id);

        return userService.findById(id)
                .map(userMapper::toDto)
                .map(dto -> {
                    log.info("User found: id={}", id);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> {
                    log.warn("User not found: id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param id  UUID пользователя
     * @param dto DTO с новыми значениями email и/или name
     * @return ResponseEntity с DTO обновленного пользователя (200 OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID id,
            @RequestBody UserDto dto) {
        log.debug("Request to update user id={} with data={}", id, dto);

        User entity = userMapper.toEntity(dto);
        User updated = userService.update(id, entity);

        log.info("User updated successfully: id={}", id);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id UUID пользователя
     * @return ResponseEntity без тела (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        log.debug("Request to delete user id={}", id);
        userService.delete(id);
        log.info("User deleted: id={}", id);

        return ResponseEntity.noContent().build();
    }
}
