package com.lunarshade.vkapp.controller;


import com.lunarshade.vkapp.dao.request.TableForm;
import com.lunarshade.vkapp.entity.Desk;
import com.lunarshade.vkapp.service.DeskService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tables")
@CrossOrigin
@Data
public class DeskController {

    private final DeskService deskService;


    @PostMapping("/{table}")
    public ResponseEntity<Void> updateDesk(@PathVariable Desk table, @RequestBody TableForm tableForm) {
        deskService.updateDesk(tableForm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{table}")
    public ResponseEntity<Void> updateDesk(@PathVariable Long table) {
        deskService.deleteTable(table);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{table}/setDefault")
    public ResponseEntity<Void> changeDefaultDesk(@PathVariable Desk table) {
        deskService.changeDefaultDesk(table);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
