package com.jinax.pm_backend.Service;

import com.jinax.pm_backend.Entity.Block;
import com.jinax.pm_backend.Exception.InvalidBlockException;
import com.jinax.pm_backend.Repository.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockService.class);
    private final BlockRepository blockRepository;

    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    public Block getBlockById(int id){
        Optional<Block> byId = blockRepository.findById(id);
        Block block = byId.orElse(null);
        LOGGER.info("getBlockById, id : {}, block: {}",id,block);
        return block;
    }
    public List<Block> getBlockByOwnerId(int ownerId){
        List<Block> byOwnerId = blockRepository.getBlocksByOwnerId(ownerId);
        return byOwnerId;
    }
    public List<Block> getBlockByPostIdPaged(int postId,int page,int size){
        Page<Block> byPostId = blockRepository.findAllByPostIdEquals(postId,PageRequest.of(page,size));
        List<Block> collect = byPostId.get().collect(Collectors.toList());
        LOGGER.info("getBlockByPostIdPaged, postId : {}, page: {},size :{}, result:{}",postId,page,size,collect);
        return collect;
    }
    public Block createBlock(Block block)throws InvalidBlockException {
        block.setIsDeleted((short) 0);
        block.setCreateTime(new Date());
        Block saveBlock = blockRepository.save(block);
        LOGGER.info("createBlock, block before create : {}, block after create : {}",block,saveBlock);
        return saveBlock;
    }
}
