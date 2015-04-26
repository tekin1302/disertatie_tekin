package ro.tekin.disertatie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.tekin.disertatie.entity.Employee;
import ro.tekin.disertatie.entity.Position;
import ro.tekin.disertatie.entity.User;
import ro.tekin.disertatie.service.TService;
import ro.tekin.disertatie.util.TUtils;
import ro.tekin.disertatie.util.wrapper.TBoolean;

import java.util.List;

/**
 * Created by tekin on 3/6/14.
 */
@Controller
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private TService service;

    @RequestMapping (value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Position> list() {
        List<Position> positions = service.getPositionsForCompany(TUtils.getAuthenticatedUser().getCompany());
        return positions;
    }

    @RequestMapping (method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Integer createPosition(@RequestBody Position position) {
        service.savePosition(position);

        return position.getId();
    }

    @RequestMapping (method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public void updatePosition(@RequestBody Position position) {
        service.updatePosition(position);
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Position getPosition(@PathVariable("id") Integer id) {
        Position position = service.getPosition(id);
        return position;
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePosition(@PathVariable("id") Integer id) {
        service.deletePosition(id);
    }
}
