import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { POWER_FEED_FEED_TYPE_OPTIONS, POWER_FEED_CRITICALITY_OPTIONS } from './types';
import type { PowerFeedFilter } from './types';

interface Props {
  value: PowerFeedFilter;
  onChange: (next: PowerFeedFilter) => void;
  onReset: () => void;
}

export function PowerFeedFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PowerFeedFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Power Feed filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Power Feed"
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
      <FilterField label="Room Id">
        <Input
          aria-label="Room Id filter"
          placeholder="UUID"
          value={value.roomId ?? ''}
          onChange={(v) => patch({ roomId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Feed Type">
        <MultiSelect
          ariaLabel="Feed Type filter"
          options={POWER_FEED_FEED_TYPE_OPTIONS}
          value={value.feedType ?? []}
          onChange={(v) => patch({ feedType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Voltage">
        <RangeInputs
          ariaLabel="Voltage"
          min={value.voltageMin}
          max={value.voltageMax}
          onChange={(min, max) => patch({ voltageMin: min, voltageMax: max })}
        />
      </FilterField>
      <FilterField label="Amperage">
        <RangeInputs
          ariaLabel="Amperage"
          min={value.amperageMin}
          max={value.amperageMax}
          onChange={(min, max) => patch({ amperageMin: min, amperageMax: max })}
        />
      </FilterField>
      <FilterField label="Phase">
        <Input
          aria-label="Phase filter"
          value={value.phase ?? ''}
          onChange={(v) => patch({ phase: v || undefined })}
        />
      </FilterField>
      <FilterField label="Breaker Label">
        <Input
          aria-label="Breaker Label filter"
          value={value.breakerLabel ?? ''}
          onChange={(v) => patch({ breakerLabel: v || undefined })}
        />
      </FilterField>
      <FilterField label="Redundancy Group">
        <Input
          aria-label="Redundancy Group filter"
          value={value.redundancyGroup ?? ''}
          onChange={(v) => patch({ redundancyGroup: v || undefined })}
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
          options={POWER_FEED_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
