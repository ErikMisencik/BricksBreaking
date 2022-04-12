package sk.tuke.gamestudio.game.bricks.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.bricks.entity.Rating;

import sk.tuke.gamestudio.game.bricks.service.rating.RatingService;

import java.util.List;


@RestController
@RequestMapping("/api/rating")   //zmenit potom url veci podla mna
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public void setRating(@RequestBody Rating rating) {ratingService.setRating(rating);}

    @GetMapping("/{game}") //{} tieto zatvorky pretoze premenna game
    public List<Rating> getTopRating(@PathVariable String game) {
        return ratingService.getTopRating(game);
    }

    @GetMapping("/{game}/AverageRating") //{} tieto zatvorky pretoze premenna game
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

}
