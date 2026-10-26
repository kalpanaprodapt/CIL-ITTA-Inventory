import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { TOPOLOGY_EDGE_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { TopologyEdgeFilter } from './types';

interface Props {
  value: TopologyEdgeFilter;
  onChange: (next: TopologyEdgeFilter) => void;
  onReset: () => void;
}

export function TopologyEdgeFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: TopologyEdgeFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Topology Edge filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Topology Edge"
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
      <FilterField label="View Id">
        <Input
          aria-label="View Id filter"
          placeholder="UUID"
          value={value.viewId ?? ''}
          onChange={(v) => patch({ viewId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Source Node Id">
        <Input
          aria-label="Source Node Id filter"
          placeholder="UUID"
          value={value.sourceNodeId ?? ''}
          onChange={(v) => patch({ sourceNodeId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Target Node Id">
        <Input
          aria-label="Target Node Id filter"
          placeholder="UUID"
          value={value.targetNodeId ?? ''}
          onChange={(v) => patch({ targetNodeId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Weight">
        <RangeInputs
          ariaLabel="Weight"
          min={value.weightMin}
          max={value.weightMax}
          onChange={(min, max) => patch({ weightMin: min, weightMax: max })}
        />
      </FilterField>
      <FilterField label="Directed">
        <TriState
          ariaLabel="Directed filter"
          value={value.directed}
          onChange={(v) => patch({ directed: v })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
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
          options={TOPOLOGY_EDGE_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
