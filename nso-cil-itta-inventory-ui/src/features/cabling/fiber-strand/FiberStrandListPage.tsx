import { useMemo, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { DataTable } from '@/shared/components/DataTable';
import { ColumnPicker } from '@/shared/components/ColumnPicker';
import { Pagination } from '@/shared/components/Pagination';
import { EmptyState, ErrorBanner, PageHeader } from '@/shared/components/page';
import { downloadBlob } from '@/shared/utils/download';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { fiberStrandColumns, fiberStrandDefaultVisible } from './columns';
import { countFiberStrandActiveFilters, parseFiberStrandFilter, serializeFiberStrandFilter } from './filterState';
import { FiberStrandFilterBar } from './FiberStrandFilterBar';
import { useChangeFiberStrandStatus, useDeleteFiberStrand, useExportFiberStrands, useFiberStrandList, useFiberStrandStatusStats } from './hooks';
import { FIBERSTRAND_TITLE_PLURAL } from './types';

const PAGE_SIZES = [10, 20, 50, 100];

export function FiberStrandListPage() {
  const [params, setParams] = useSearchParams();
  const filter = useMemo(() => parseFiberStrandFilter(params), [params]);
  const page = Number(params.get('page') ?? 0);
  const size = Number(params.get('size') ?? 20);
  const sort = params.get('sort') ?? 'code,asc';
  const [visible, setVisible] = useState<string[]>(fiberStrandDefaultVisible);
  const [selected, setSelected] = useState<string[]>([]);

  const list = useFiberStrandList(filter, page, size, sort);
  const stats = useFiberStrandStatusStats();
  const changeStatus = useChangeFiberStrandStatus();
  const remove = useDeleteFiberStrand();
  const exporter = useExportFiberStrands();

  function update(next: Record<string, string | undefined>) {
    const merged = new URLSearchParams(params);
    for (const [key, value] of Object.entries(next)) {
      if (value === undefined) merged.delete(key);
      else merged.set(key, value);
    }
    setParams(merged, { replace: true });
  }

  function applyFilter(next: typeof filter) {
    const base = serializeFiberStrandFilter(next);
    base.set('size', String(size));
    base.set('sort', sort);
    setParams(base, { replace: true });
    setSelected([]);
  }

  async function onExport() {
    const blob = await exporter.mutateAsync(filter);
    downloadBlob(blob, 'fiber-strands.csv');
  }

  function bulkStatus(status: LifecycleStatus) {
    selected.forEach((id) => changeStatus.mutate({ id, status }));
    setSelected([]);
  }

  function bulkDelete() {
    selected.forEach((id) => remove.mutate(id));
    setSelected([]);
  }

  const columns = fiberStrandColumns.filter((c) => visible.includes(c.key));
  const active = countFiberStrandActiveFilters(filter);

  return (
    <section aria-labelledby="fiber-strand-list-title">
      <PageHeader
        id="fiber-strand-list-title"
        title={FIBERSTRAND_TITLE_PLURAL}
        subtitle={stats.data ? `${Object.values(stats.data).reduce((a, b) => a + b, 0)} records` : undefined}
        actions={
          <>
            <Button variant="secondary" onClick={onExport} disabled={exporter.isPending}>
              Export CSV
            </Button>
            <Link className="btn btn--primary" to="/cabling/fiber-strands/new">
              New Fiber Strand
            </Link>
          </>
        }
      />
      <FiberStrandFilterBar value={filter} onChange={applyFilter} onReset={() => applyFilter({})} />
      <div className="list-toolbar">
        <span>{active} active filter{active === 1 ? '' : 's'}</span>
        <ColumnPicker columns={fiberStrandColumns} visible={visible} onChange={setVisible} />
      </div>
      {selected.length > 0 ? (
        <div className="bulk-bar" role="region" aria-label="Bulk actions">
          <span>{selected.length} selected</span>
          {LIFECYCLE_STATUSES.map((s) => (
            <Button key={s} variant="ghost" onClick={() => bulkStatus(s)}>
              Set {s}
            </Button>
          ))}
          <Button variant="danger" onClick={bulkDelete}>
            Delete selected
          </Button>
        </div>
      ) : null}
      {list.isError ? (
        <ErrorBanner message="Could not load Fiber Strand records." onRetry={() => list.refetch()} />
      ) : list.data && list.data.content.length === 0 ? (
        <EmptyState title="No Fiber Strand records" hint="Adjust the filters or create a new record." />
      ) : (
        <DataTable
          columns={columns}
          rows={list.data?.content ?? []}
          rowKey={(row) => row.id}
          loading={list.isLoading}
          sort={sort}
          onSort={(next) => update({ sort: next, page: '0' })}
          selectable
          selected={selected}
          onSelectionChange={setSelected}
        />
      )}
      <Pagination
        page={list.data?.page ?? page}
        size={size}
        totalElements={list.data?.totalElements ?? 0}
        totalPages={list.data?.totalPages ?? 0}
        sizes={PAGE_SIZES}
        onPage={(p) => update({ page: String(p) })}
        onSize={(s) => update({ size: String(s), page: '0' })}
      />
    </section>
  );
}
