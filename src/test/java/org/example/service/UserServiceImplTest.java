package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для UserService.
 */
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveUser_andReturnSaved() {
        User input = User.builder()
                .email("test@example.com")
                .name("Test User")
                .build();

        User saved = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .name("Test User")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.create(input);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User toSave = captor.getValue();

        assertEquals(input.getEmail(), toSave.getEmail());
        assertEquals(input.getName(), toSave.getName());
        assertNotNull(result.getId());
        assertEquals(saved.getEmail(), result.getEmail());
    }

    @Test
    void findById_existingId_shouldReturnOptionalWithUser() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).email("a@b.com").name("A B").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> opt = userService.findById(id);

        assertTrue(opt.isPresent());
        assertEquals(user, opt.get());
    }

    @Test
    void findById_nonExistingId_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> opt = userService.findById(id);

        assertFalse(opt.isPresent());
    }
}
