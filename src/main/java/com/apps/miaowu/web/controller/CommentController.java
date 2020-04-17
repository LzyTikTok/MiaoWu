
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "addComment")
    public APIResult addComment(User user, Comment comment){
        return commentService.addComment(user, comment);
    }

    @PostMapping(value = "deleteComment")
    public APIResult deleteComment(User user, Comment comment){
        return commentService.deleteComment(user, comment);
    }

    @PostMapping(value = "updateComment")
    public APIResult updateComment(User user, Comment comment){
        return commentService.updateComment(user, comment);
    }
}