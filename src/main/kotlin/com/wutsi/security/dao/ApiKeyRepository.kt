package com.wutsi.security.dao

import com.wutsi.security.entity.ApiKeyEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ApiKeyRepository : CrudRepository<ApiKeyEntity, String>
