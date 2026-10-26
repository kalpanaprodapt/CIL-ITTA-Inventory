-- K8s Cluster (Virtual and Cloud)
create table inv_k8s_cluster (
    id uuid primary key,
    tenant_id varchar(64) not null,
    version bigint not null default 0,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    created_by varchar(128),
    updated_by varchar(128),
    code varchar(255) not null,
    name varchar(255) not null,
    description varchar(4000),
    status varchar(40) not null,
    external_ref varchar(255),
    cluster_name varchar(255) not null,
    cluster_version varchar(255) not null,
    node_count integer not null,
    cloud_account_id uuid,
    region varchar(255),
    api_endpoint varchar(255),
    effective_to date,
    labels varchar(255),
    lifecycle_stage varchar(40),
    source_system varchar(255),
    owner varchar(255),
    constraint uk_inv_k8s_cluster_tenant_code unique (tenant_id, code)
);

create index ix_inv_k8s_cluster_status on inv_k8s_cluster (tenant_id, status);
create index ix_inv_k8s_cluster_name on inv_k8s_cluster (tenant_id, name);
create index ix_inv_k8s_cluster_ext on inv_k8s_cluster (external_ref);
create index ix_inv_k8s_cluster_cloud_account_id on inv_k8s_cluster (tenant_id, cloud_account_id);

comment on table inv_k8s_cluster is 'K8s Cluster - Virtual and Cloud';
