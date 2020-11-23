package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.ContractPayload;
import com.hongdatchy.entities.payload.DetectorPayload;
import com.hongdatchy.service.ContractService;
import com.hongdatchy.service.DetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractController {

    @Autowired
    ContractService contractService;

    @PostMapping("api/contract")
    public ResponseEntity<Object> createAndUpdate(@RequestBody ContractPayload contractPayload){
        return ResponseEntity.ok(MyResponse.success(contractService.createAndUpdate(contractPayload)));
    }

    @GetMapping("api/contract/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(contractService.findAll()));
    }

    @DeleteMapping("api/contract/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(contractService.delete(id)));
    }

}
