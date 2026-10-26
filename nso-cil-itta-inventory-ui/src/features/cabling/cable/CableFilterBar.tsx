import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CABLE_CABLE_TYPE_OPTIONS, CABLE_CRITICALITY_OPTIONS } from './types';
import type { CableFilter } from './types';

interface Props {
  value: CableFilter;
  onChange: (next: CableFilter) => void;
  onReset: () => void;
}

export function CableFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CableFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Cable filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Cable"
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
      <FilterField label="Cable Type">
        <MultiSelect
          ariaLabel="Cable Type filter"
          options={CABLE_CABLE_TYPE_OPTIONS}
          value={value.cableType ?? []}
          onChange={(v) => patch({ cableType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Length M">
        <RangeInputs
          ariaLabel="Length M"
          min={value.lengthMMin}
          max={value.lengthMMax}
          onChange={(min, max) => patch({ lengthMMin: min, lengthMMax: max })}
        />
      </FilterField>
      <FilterField label="A End Port Id">
        <Input
          aria-label="A End Port Id filter"
          placeholder="UUID"
          value={value.aEndPortId ?? ''}
          onChange={(v) => patch({ aEndPortId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Z End Port Id">
        <Input
          aria-label="Z End Port Id filter"
          placeholder="UUID"
          value={value.zEndPortId ?? ''}
          onChange={(v) => patch({ zEndPortId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Color">
        <Input
          aria-label="Color filter"
          value={value.color ?? ''}
          onChange={(v) => patch({ color: v || undefined })}
        />
      </FilterField>
      <FilterField label="Label">
        <Input
          aria-label="Label filter"
          value={value.label ?? ''}
          onChange={(v) => patch({ label: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={CABLE_CRITICALITY_OPTIONS}
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
