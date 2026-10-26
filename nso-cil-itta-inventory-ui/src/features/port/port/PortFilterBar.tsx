import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { PORT_MEDIA_TYPE_OPTIONS, PORT_ADMIN_STATE_OPTIONS, PORT_CRITICALITY_OPTIONS } from './types';
import type { PortFilter } from './types';

interface Props {
  value: PortFilter;
  onChange: (next: PortFilter) => void;
  onReset: () => void;
}

export function PortFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PortFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Port filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Port"
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
      <FilterField label="Card Id">
        <Input
          aria-label="Card Id filter"
          placeholder="UUID"
          value={value.cardId ?? ''}
          onChange={(v) => patch({ cardId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Port Number">
        <Input
          aria-label="Port Number filter"
          value={value.portNumber ?? ''}
          onChange={(v) => patch({ portNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Speed Mbps">
        <RangeInputs
          ariaLabel="Speed Mbps"
          min={value.speedMbpsMin}
          max={value.speedMbpsMax}
          onChange={(min, max) => patch({ speedMbpsMin: min, speedMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Media Type">
        <MultiSelect
          ariaLabel="Media Type filter"
          options={PORT_MEDIA_TYPE_OPTIONS}
          value={value.mediaType ?? []}
          onChange={(v) => patch({ mediaType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Admin State">
        <MultiSelect
          ariaLabel="Admin State filter"
          options={PORT_ADMIN_STATE_OPTIONS}
          value={value.adminState ?? []}
          onChange={(v) => patch({ adminState: v.length ? v : undefined })}
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
          options={PORT_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
