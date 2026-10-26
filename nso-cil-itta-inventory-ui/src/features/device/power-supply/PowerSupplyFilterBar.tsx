import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { POWER_SUPPLY_CRITICALITY_OPTIONS } from './types';
import type { PowerSupplyFilter } from './types';

interface Props {
  value: PowerSupplyFilter;
  onChange: (next: PowerSupplyFilter) => void;
  onReset: () => void;
}

export function PowerSupplyFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PowerSupplyFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Power Supply filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Power Supply"
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
      <FilterField label="Chassis Id">
        <Input
          aria-label="Chassis Id filter"
          placeholder="UUID"
          value={value.chassisId ?? ''}
          onChange={(v) => patch({ chassisId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Slot Number">
        <RangeInputs
          ariaLabel="Slot Number"
          min={value.slotNumberMin}
          max={value.slotNumberMax}
          onChange={(min, max) => patch({ slotNumberMin: min, slotNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Wattage">
        <RangeInputs
          ariaLabel="Wattage"
          min={value.wattageMin}
          max={value.wattageMax}
          onChange={(min, max) => patch({ wattageMin: min, wattageMax: max })}
        />
      </FilterField>
      <FilterField label="Feed Id">
        <Input
          aria-label="Feed Id filter"
          placeholder="UUID"
          value={value.feedId ?? ''}
          onChange={(v) => patch({ feedId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Serial Number">
        <Input
          aria-label="Serial Number filter"
          value={value.serialNumber ?? ''}
          onChange={(v) => patch({ serialNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Efficiency">
        <RangeInputs
          ariaLabel="Efficiency"
          min={value.efficiencyMin}
          max={value.efficiencyMax}
          onChange={(min, max) => patch({ efficiencyMin: min, efficiencyMax: max })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={POWER_SUPPLY_CRITICALITY_OPTIONS}
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
