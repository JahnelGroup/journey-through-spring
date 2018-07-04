package com.example.basicbeans.math;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MathController {

    private MathService mathService;

    /**
     * Injection via Constructor, notice that we're injecting the interface
     * and not the concrete class.
     */
    MathController(MathService mathService){
        this.mathService = mathService;
    }

    /**
     * @RequestParam to capture URL parameters ?a=1&b=2
     */

    @GetMapping("/math/add2")
    public int add2(@RequestParam Integer a, @RequestParam Integer b){
        return mathService.add(a, b);
    }

    @GetMapping("/math/sub2")
    public int sub2(@RequestParam Integer a, @RequestParam Integer b){
        return mathService.sub(a, b);
    }

    /**
     * You can also use it to capture lists ?num=1,2,3
     */

    @GetMapping("/math/add")
    public int addList(@RequestParam List<Integer> nums){
        return mathService.add(nums.toArray(new Integer[nums.size()]));
    }

    @GetMapping("/math/sub")
    public int subList(@RequestParam List<Integer> nums){
        return mathService.sub(nums.toArray(new Integer[nums.size()]));
    }

}
