package hr.algebra.concertcrew.controller.rest;

import hr.algebra.concertcrew.service.DatabaseBackupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/database")
@Tag(name = "Database", description = "Whole-database backup and restore (admin only)")
@SecurityRequirement(name = "bearerAuth")
public class DatabaseBackupController {

    private final DatabaseBackupService backupService;

    public DatabaseBackupController(DatabaseBackupService backupService) {
        this.backupService = backupService;
    }

    @PostMapping("/backup")
    @Operation(summary = "Create a backup of the whole database (admin only)")
    public ResponseEntity<String> backup() {
        try {
            Path file = backupService.backup();
            return ResponseEntity.ok("Database backup created at: " + file);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Backup failed: " + e.getMessage());
        }
    }

    @PostMapping("/restore")
    @Operation(summary = "Restore the whole database from the latest backup (admin only)")
    public ResponseEntity<String> restore() {
        try {
            Path file = backupService.restore();
            return ResponseEntity.ok("Database restored from: " + file);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Restore failed: " + e.getMessage());
        }
    }
}