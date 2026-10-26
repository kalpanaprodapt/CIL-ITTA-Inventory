import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { TOPOLOGY_VIEW_VIEW_TYPE_OPTIONS, TOPOLOGY_VIEW_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { TopologyViewFilter } from './types';

interface Props {
  value: TopologyViewFilter;
  onChange: (next: TopologyViewFilter) => void;
  onReset: () => void;
}

export function TopologyViewFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: TopologyViewFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Topology View filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Topology View"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="View Name">
        <Input
          aria-label="View Name filter"
          value={value.viewName ?? ''}
          onChange={(v) => patch({ viewName: v || undefined })}
        />
      </FilterField>
      <FilterField label="View Type">
        <MultiSelect
          ariaLabel="View Type filter"
          options={TOPOLOGY_VIEW_VIEW_TYPE_OPTIONS}
          value={value.viewType ?? []}
          onChange={(v) => patch({ viewType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Scope Filter">
        <Input
          aria-label="Scope Filter filter"
          value={value.scopeFilter ?? ''}
          onChange={(v) => patch({ scopeFilter: v || undefined })}
        />
      </FilterField>
      <FilterField label="Refreshed At">
        <DateRange
          ariaLabel="Refreshed At"
          from={value.refreshedAtFrom}
          to={value.refreshedAtTo}
          onChange={(from, to) => patch({ refreshedAtFrom: from, refreshedAtTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={TOPOLOGY_VIEW_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
