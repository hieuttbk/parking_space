package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Invoice;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("api/ad/invoice/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Invoice invoice){
        return ResponseEntity.ok(MyResponse.success(invoiceService.createAndUpdate(invoice)));
    }

    @GetMapping("api/ad/invoice/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(invoiceService.findAll()));
    }

    @DeleteMapping("api/ad/invoice/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(invoiceService.delete(id)));
    }
}
