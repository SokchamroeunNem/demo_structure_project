#!/bin/bash

FEATURE_NAME=$1

if [ -z "$FEATURE_NAME" ]; then
    echo "Usage: ./generate_feature.sh Post"
    exit 1
fi

BASE_PACKAGE="com.example.demo"
BASE_SRC_PATH="app/src/main/java/com/example/demo"

FEATURE_LOWER=$(echo "$FEATURE_NAME" | tr '[:upper:]' '[:lower:]')

echo "Generating feature: $FEATURE_NAME"

# =========================
# DATA LAYER
# =========================

mkdir -p "$BASE_SRC_PATH/data/$FEATURE_LOWER/mapper"
mkdir -p "$BASE_SRC_PATH/data/$FEATURE_LOWER/remote/api"
mkdir -p "$BASE_SRC_PATH/data/$FEATURE_LOWER/remote/request"
mkdir -p "$BASE_SRC_PATH/data/$FEATURE_LOWER/remote/response"
mkdir -p "$BASE_SRC_PATH/data/$FEATURE_LOWER/repository"

# Mapper
cat > "$BASE_SRC_PATH/data/$FEATURE_LOWER/mapper/${FEATURE_NAME}Mapper.kt" << EOF
package $BASE_PACKAGE.data.$FEATURE_LOWER.mapper

object ${FEATURE_NAME}Mapper {

}
EOF

# Api
cat > "$BASE_SRC_PATH/data/$FEATURE_LOWER/remote/api/${FEATURE_NAME}Api.kt" << EOF
package $BASE_PACKAGE.data.$FEATURE_LOWER.remote.api

interface ${FEATURE_NAME}Api {

}
EOF

# Request
cat > "$BASE_SRC_PATH/data/$FEATURE_LOWER/remote/request/${FEATURE_NAME}Request.kt" << EOF
package $BASE_PACKAGE.data.$FEATURE_LOWER.remote.request

data class ${FEATURE_NAME}Request(
    val id: String
)
EOF

# Response
cat > "$BASE_SRC_PATH/data/$FEATURE_LOWER/remote/response/${FEATURE_NAME}Response.kt" << EOF
package $BASE_PACKAGE.data.$FEATURE_LOWER.remote.response

data class ${FEATURE_NAME}Response(
    val id: String
)
EOF

# Repository Impl
cat > "$BASE_SRC_PATH/data/$FEATURE_LOWER/repository/${FEATURE_NAME}RepositoryImpl.kt" << EOF
package $BASE_PACKAGE.data.$FEATURE_LOWER.repository

import $BASE_PACKAGE.domain.$FEATURE_LOWER.${FEATURE_NAME}Repository

class ${FEATURE_NAME}RepositoryImpl : ${FEATURE_NAME}Repository {

}
EOF

# Module
cat > "$BASE_SRC_PATH/data/$FEATURE_LOWER/${FEATURE_NAME}Module.kt" << EOF
package $BASE_PACKAGE.data.$FEATURE_LOWER

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ${FEATURE_NAME}Module {

}
EOF

# =========================
# DOMAIN LAYER
# =========================

mkdir -p "$BASE_SRC_PATH/domain/$FEATURE_LOWER/entity"
mkdir -p "$BASE_SRC_PATH/domain/$FEATURE_LOWER/use_case"

# Entity
cat > "$BASE_SRC_PATH/domain/$FEATURE_LOWER/entity/${FEATURE_NAME}Entity.kt" << EOF
package $BASE_PACKAGE.domain.$FEATURE_LOWER.entity

data class ${FEATURE_NAME}Entity(
    val id: String
)
EOF

# Repository Interface
cat > "$BASE_SRC_PATH/domain/$FEATURE_LOWER/${FEATURE_NAME}Repository.kt" << EOF
package $BASE_PACKAGE.domain.$FEATURE_LOWER

interface ${FEATURE_NAME}Repository {

}
EOF

# UseCase
cat > "$BASE_SRC_PATH/domain/$FEATURE_LOWER/use_case/${FEATURE_NAME}UseCase.kt" << EOF
package $BASE_PACKAGE.domain.$FEATURE_LOWER.use_case

import javax.inject.Inject

class ${FEATURE_NAME}UseCase @Inject constructor() {

}
EOF

# =========================
# PRESENTATION LAYER
# =========================

mkdir -p "$BASE_SRC_PATH/presentation/${FEATURE_LOWER}screen"

# Screen
cat > "$BASE_SRC_PATH/presentation/${FEATURE_LOWER}screen/${FEATURE_NAME}Screen.kt" << EOF
package $BASE_PACKAGE.presentation.${FEATURE_LOWER}screen

import androidx.compose.runtime.Composable

@Composable
fun ${FEATURE_NAME}Screen() {

}
EOF

# ViewModel
cat > "$BASE_SRC_PATH/presentation/${FEATURE_LOWER}screen/${FEATURE_NAME}ViewModel.kt" << EOF
package $BASE_PACKAGE.presentation.${FEATURE_LOWER}screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ${FEATURE_NAME}ViewModel @Inject constructor() : ViewModel() {

}
EOF

echo "Feature $FEATURE_NAME generated successfully!"