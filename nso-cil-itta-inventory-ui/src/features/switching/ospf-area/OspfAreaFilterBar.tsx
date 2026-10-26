import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { OSPF_AREA_AREA_TYPE_OPTIONS, OSPF_AREA_CRITICALITY_OPTIONS } from './types';
import type { OspfAreaFilter } from './types';

interface Props {
  value: OspfAreaFilter;
  onChange: (next: OspfAreaFilter) => void;
  onReset: () => void;
}

export function OspfAreaFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: OspfAreaFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Ospf Area filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Ospf Area"
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
      <FilterField label="Area Id">
        <Input
          aria-label="Area Id filter"
          value={value.areaId ?? ''}
          onChange={(v) => patch({ areaId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Area Type">
        <MultiSelect
          ariaLabel="Area Type filter"
          options={OSPF_AREA_AREA_TYPE_OPTIONS}
          value={value.areaType ?? []}
          onChange={(v) => patch({ areaType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Device Count">
        <RangeInputs
          ariaLabel="Device Count"
          min={value.deviceCountMin}
          max={value.deviceCountMax}
          onChange={(min, max) => patch({ deviceCountMin: min, deviceCountMax: max })}
        />
      </FilterField>
      <FilterField label="Auth Type">
        <Input
          aria-label="Auth Type filter"
          value={value.authType ?? ''}
          onChange={(v) => patch({ authType: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={OSPF_AREA_CRITICALITY_OPTIONS}
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
