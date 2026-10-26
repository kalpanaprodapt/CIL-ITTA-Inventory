import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SERVICE_ENDPOINT_ENDPOINT_ROLE_LABELS, SERVICE_ENDPOINT_LIFECYCLE_STAGE_LABELS } from './types';
import type { ServiceEndpoint } from './types';

export const serviceEndpointColumns: Column<ServiceEndpoint>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/service/service-endpoints/${row.id}`}>{row.code}</RouterLink>,
  },
  {
    key: 'name',
    header: 'Name',
    sortable: true,
    align: 'left',
    render: (row) => row.name ?? '—',
  },
  {
    key: 'description',
    header: 'Description',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.description, 60),
  },
  {
    key: 'status',
    header: 'Status',
    sortable: true,
    align: 'left',
    render: (row) => <StatusBadge status={row.status} />,
  },
  {
    key: 'externalRef',
    header: 'External Ref',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.externalRef ?? '—',
  },
  {
    key: 'serviceId',
    header: 'Service Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.serviceId} />,
  },
  {
    key: 'siteId',
    header: 'Site Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.siteId} />,
  },
  {
    key: 'portId',
    header: 'Port Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.portId} />,
  },
  {
    key: 'endpointRole',
    header: 'Endpoint Role',
    sortable: true,
    align: 'left',
    render: (row) => row.endpointRole ? SERVICE_ENDPOINT_ENDPOINT_ROLE_LABELS[row.endpointRole] : '—',
  },
  {
    key: 'handoff',
    header: 'Handoff',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.handoff ?? '—',
  },
  {
    key: 'notes',
    header: 'Notes',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.notes, 60),
  },
  {
    key: 'effectiveFrom',
    header: 'Effective From',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveFrom),
  },
  {
    key: 'effectiveTo',
    header: 'Effective To',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveTo),
  },
  {
    key: 'labels',
    header: 'Labels',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.labels ?? '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? SERVICE_ENDPOINT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const serviceEndpointDefaultVisible = serviceEndpointColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
