package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GatewayController {

    @Autowired
    GatewayService gatewayService;

    @Autowired
    JWTService jwtService;

    @PostMapping("api/ad/gateway/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Gateway gateway){
        return ResponseEntity.ok(MyResponse.success(gatewayService.createAndUpdate(gateway)));
    }

    @GetMapping("api/ad/gateway/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(gatewayService.findAll()));
    }

    @DeleteMapping("api/ad/gateway/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(gatewayService.delete(id)));
    }

    @GetMapping(value = {"api/mn/gateway/find_all"})
    public ResponseEntity<Object> managerFind(@RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(gatewayService.managerFind(phone)));
    }

    @PostMapping("api/mn/gateway/update")
    public ResponseEntity<Object> managerUpdate(@RequestBody Gateway gateway, @RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(gatewayService.managerUpdate(gateway, phone)));
    }

    @DeleteMapping("api/mn/gateway/delete/{id}")
    public ResponseEntity<Object> manageDelete(@PathVariable int id, @RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(gatewayService.managerDelete(id, phone)));
    }

}
