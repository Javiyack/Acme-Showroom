package services;

import domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.CommentRepository;

import java.util.Collection;

@Service
@Transactional
public class CommentService {
    //Repositories
    @Autowired
    private CommentRepository commentRepository;
    //Services
    @Autowired
    private ActorService actorService;

    //Constructor
    public CommentService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Comment create() {
        final Comment result = new Comment();
        return result;
    }

    public Comment save(Comment foolow) {
        return commentRepository.save(foolow);

    }

    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findOne(int followId) {
        return commentRepository.findOne(followId);
    }

    public Collection<Comment> findByPlace(Integer id) {
        return commentRepository.findByPlace(id);
    }
}
