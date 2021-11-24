package com.jinax.pm_backend.Service;

import com.jinax.pm_backend.Entity.Reply;
import com.jinax.pm_backend.Exception.InvalidReplyException;
import com.jinax.pm_backend.Repository.ReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ReplyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplyService.class);
    private final ReplyRepository replyRepository;
    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }
    public Reply getReplyById(int id){
        Optional<Reply> byId = replyRepository.findById(id);
        Reply reply = byId.orElse(null);
        LOGGER.info("getReplyById, id:{},reply: {}",id,reply);
        return reply;
    }
    public Reply createReply(Reply reply) throws InvalidReplyException {
        reply.setIsDeleted((short) 0);
        reply.setCreateTime(new Date());
        Reply saveReply = replyRepository.save(reply);
        LOGGER.info("createReply, reply before create:{},reply after create:{}",reply,saveReply);
        return saveReply;
    }
}
