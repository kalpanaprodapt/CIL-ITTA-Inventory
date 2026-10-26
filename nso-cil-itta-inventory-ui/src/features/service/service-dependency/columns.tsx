import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SERVICE_DEPENDENCY_DEPENDENCY_TYPE_LABELS, SERVICE_DEPENDENCY_CRITICALITY_LABELS } from './types';
import type { ServiceDependency } from './types';

export const serviceDependencyColumns: Column<ServiceDependency>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/service/service-dependencies/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'dependsOnId',
    header: 'Depends On Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.dependsOnId} />,
  },
  {
    key: 'dependencyType',
    header: 'Dependency Type',
    sortable: true,
    align: 'left',
    render: (row) => row.dependencyType ? SERVICE_DEPENDENCY_DEPENDENCY_TYPE_LABELS[row.dependencyType] : '—',
  },
  {
    key: 'impactScore',
    header: 'Impact Score',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.impactScore),
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? SERVICE_DEPENDENCY_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
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
];

export const serviceDependencyDefaultVisible = serviceDependencyColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
