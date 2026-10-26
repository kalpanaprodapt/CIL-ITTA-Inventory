import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CHASSIS_LIFECYCLE_STAGE_OPTIONS, CHASSIS_CRITICALITY_OPTIONS } from './types';
import type { ChassisFilter } from './types';

interface Props {
  value: ChassisFilter;
  onChange: (next: ChassisFilter) => void;
  onReset: () => void;
}

export function ChassisFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ChassisFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Chassis filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Chassis"
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
      <FilterField label="Device Id">
        <Input
          aria-label="Device Id filter"
          placeholder="UUID"
          value={value.deviceId ?? ''}
          onChange={(v) => patch({ deviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Slot Count">
        <RangeInputs
          ariaLabel="Slot Count"
          min={value.slotCountMin}
          max={value.slotCountMax}
          onChange={(min, max) => patch({ slotCountMin: min, slotCountMax: max })}
        />
      </FilterField>
      <FilterField label="Serial Number">
        <Input
          aria-label="Serial Number filter"
          value={value.serialNumber ?? ''}
          onChange={(v) => patch({ serialNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Backplane Gbps">
        <RangeInputs
          ariaLabel="Backplane Gbps"
          min={value.backplaneGbpsMin}
          max={value.backplaneGbpsMax}
          onChange={(min, max) => patch({ backplaneGbpsMin: min, backplaneGbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Redundancy Mode">
        <Input
          aria-label="Redundancy Mode filter"
          value={value.redundancyMode ?? ''}
          onChange={(v) => patch({ redundancyMode: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={CHASSIS_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={CHASSIS_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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
