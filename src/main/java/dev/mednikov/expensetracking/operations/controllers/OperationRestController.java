package dev.mednikov.expensetracking.operations.controllers;

import dev.mednikov.expensetracking.operations.dto.OperationDto;
import dev.mednikov.expensetracking.operations.services.OperationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operations")
public class OperationRestController {

    private final OperationService operationService;

    public OperationRestController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody OperationDto createOperation (@RequestBody @Valid OperationDto body){
        return this.operationService.createOperation(body);
    }

    @PutMapping("/update")
    public @ResponseBody OperationDto updateOperation (@RequestBody @Valid OperationDto body){
        return this.operationService.updateOperation(body);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperation (@PathVariable Long id){
        this.operationService.deleteOperation(id);
    }

    @GetMapping("/operation/{id}")
    public ResponseEntity<OperationDto> getOperationById (@PathVariable Long id){
        Optional<OperationDto> result = this.operationService.findOperationById(id);
        return ResponseEntity.of(result);
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody List<OperationDto> getAllOperationsForUser(@PathVariable Long userId){
        return this.operationService.findAllOperationsForUser(userId);
    }

}
