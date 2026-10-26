import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { TOPOLOGY_NODE_CRITICALITY_OPTIONS } from './types';
import type { TopologyNodeFilter } from './types';

interface Props {
  value: TopologyNodeFilter;
  onChange: (next: TopologyNodeFilter) => void;
  onReset: () => void;
}

export function TopologyNodeFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: TopologyNodeFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Topology Node filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Topology Node"
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
      <FilterField label="Entity Ref">
        <Input
          aria-label="Entity Ref filter"
          value={value.entityRef ?? ''}
          onChange={(v) => patch({ entityRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Node Type">
        <Input
          aria-label="Node Type filter"
          value={value.nodeType ?? ''}
          onChange={(v) => patch({ nodeType: v || undefined })}
        />
      </FilterField>
      <FilterField label="X Pos">
        <RangeInputs
          ariaLabel="X Pos"
          min={value.xPosMin}
          max={value.xPosMax}
          onChange={(min, max) => patch({ xPosMin: min, xPosMax: max })}
        />
      </FilterField>
      <FilterField label="Y Pos">
        <RangeInputs
          ariaLabel="Y Pos"
          min={value.yPosMin}
          max={value.yPosMax}
          onChange={(min, max) => patch({ yPosMin: min, yPosMax: max })}
        />
      </FilterField>
      <FilterField label="Layer">
        <RangeInputs
          ariaLabel="Layer"
          min={value.layerMin}
          max={value.layerMax}
          onChange={(min, max) => patch({ layerMin: min, layerMax: max })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={TOPOLOGY_NODE_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
